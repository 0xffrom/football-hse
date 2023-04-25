import { useCallback, useMemo } from 'react';
import { Typography } from 'antd';
import classNames from 'classnames';

import { getTime } from '../../../shared/lib/getTme.utils';
import { ChatRequest } from '../../../shared/lib/Requests.types';
import { ADMIN_PHONE_NUMBER } from '../../../shared/config';

import cn from './DialogPreview.module.scss';

const { Text } = Typography;

interface DialogPreviewProps {
  dialog: ChatRequest
  openDialog: (dialogId: string, pickedUserName: string) => void;
  openedDialogId?:string
}

export const DialogPreview = ({ dialog, openDialog, openedDialogId }: DialogPreviewProps) => {
  const dialogId = useMemo(() => {
    if (dialog.phoneNumber1 !== ADMIN_PHONE_NUMBER) return dialog.phoneNumber1;
    return dialog.phoneNumber2;
  }, [dialog.phoneNumber1, dialog.phoneNumber2]);

  const userName = useMemo(() => {
    if (dialog.phoneNumber1 !== ADMIN_PHONE_NUMBER) return dialog.name1;
    return dialog.name2;
  }, [dialog.name1, dialog.name2, dialog.phoneNumber1]);

  const openDialogHandler = useCallback(
    () => {
      if (dialogId) {
        openDialog(
          dialogId,
          userName ?? '',
        );
      }
    },
    [dialogId, openDialog, userName],
  );

  const isOpened = useMemo(
    () => dialogId === openedDialogId,
    [dialogId, openedDialogId],
  );

  return (
    <div
      className={classNames(cn.dialogPreviewContainer, isOpened && cn.dialogPreviewContainerOpened)}
      onClick={openDialogHandler}
    >
      <div className={cn.dialogPreviewContent}>
        {/* <div>avatar</div> */}
        <div className={cn.dialogPreviewInfo}>
          <Text strong>{dialog?.name1 ?? ''}</Text>
          <Text className={cn.messagePreview} type="secondary">{dialog?.lastMessage?.text ?? ''}</Text>
        </div>

        <div className={cn.dialogPreviewTime}>
          <Text type="secondary">
            {getTime(dialog?.lastMessage?.sendTime ?? '')}
          </Text>
        </div>
      </div>
    </div>
  );
};
