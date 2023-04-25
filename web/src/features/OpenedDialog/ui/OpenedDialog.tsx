import {
  Button, Card, Input, Space, Typography,
} from 'antd';
import { HubConnectionBuilder } from '@microsoft/signalr';

import React, {
  ChangeEvent, useCallback, useEffect, useState,
} from 'react';

import { ADMIN_PHONE_NUMBER } from '../../../shared/config';
import { AdministrationApi } from '../../../shared/api';
import { MessageRequest } from '../../../shared/lib/Requests.types';
import { Message } from '../../Message';

import cn from './OpenedDialog.module.scss';

const { Text } = Typography;

interface OpenedDialogProps {
  openedDialogId?: string;
  userName?: string
}

export const OpenedDialog = (
  { openedDialogId, userName }: OpenedDialogProps,
) => {
  const [dialogInfo, setDialogInfo] = useState<MessageRequest[] | undefined>();
  const [newMessageText, setNewMessageText] = useState<string>();

  const refetchDialogInfo = useCallback(
    (dialogId: string) => {
      AdministrationApi
        .getMessages(dialogId, 0)
        .then((dialogInfoData) => {
          setDialogInfo(dialogInfoData.reverse());
        });
    },
    [],
  );

  useEffect(() => {
    const timerID = setInterval(() => {
      if (openedDialogId && dialogInfo) {
        refetchDialogInfo(openedDialogId);
      }
    }, 3000);
    return () => {
      clearInterval(timerID);
    };
  }, [dialogInfo, openedDialogId]);

  useEffect(() => {
    // const connection = new HubConnectionBuilder()
    //   .withUrl('http://hse-football.ru/chat')
    //   .withAutomaticReconnect()
    //   .build();

    // connection.start().then((res) => {
    //   console.log('res', res);

    //   connection.on('Messages', (message) => {
    //     console.log('message', message);
    //   });
    // });

    if (openedDialogId) {
      refetchDialogInfo(openedDialogId);
    }

    // return () => {
    //   connection.start();
    // };
  }, [openedDialogId, refetchDialogInfo]);

  const changeNewMessageText = useCallback(
    (event: ChangeEvent<HTMLInputElement>) => {
      setNewMessageText(event.target.value);
    },
    [],
  );

  const sendMessage = useCallback(
    () => {
      if (newMessageText) {
        AdministrationApi.sendMessage({
          text: newMessageText,
          sendTime: (new Date()).toISOString(),
          sender: ADMIN_PHONE_NUMBER,
          receiver: openedDialogId,
        }).then((value) => {
          if (openedDialogId) refetchDialogInfo(openedDialogId);
          setNewMessageText('');
        });
      }
    },
    [newMessageText, openedDialogId, refetchDialogInfo],
  );

  return (
    <div className={cn.openedDialogCard}>
      <div className={cn.cardHeader}>
        <Text strong>{userName ?? ''}</Text>
      </div>

      <div className={cn.openedDialogContainer}>
        <div className={cn.messagesBody}>
          {dialogInfo?.map((message, key) => (
            <Message
              key={message.id}
              message={message}
            />
          ))}
        </div>

        {dialogInfo && (
        <div className={cn.messageFooter}>
          <Input width="100%" value={newMessageText} onChange={changeNewMessageText} />
          <Button
            onClick={sendMessage}
            type="primary"
          >
            Отправить
          </Button>
        </div>
        )}
      </div>
    </div>
  );
};
