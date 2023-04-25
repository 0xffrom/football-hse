import { useCallback, useEffect, useState } from 'react';

import { Dialogs } from '../../../features/Dialogs';
import { OpenedDialog } from '../../../features/OpenedDialog';
import { AdministrationApi } from '../../../shared/api';
import { ChatRequest } from '../../../shared/lib/Requests.types';

import cn from './Chat.module.scss';

export const Chat = () => {
  const [dialogs, setDialogs] = useState<ChatRequest[] | undefined>(undefined);
  const [openedDialogId, setOpenedDialogId] = useState<string | undefined>(undefined);
  const [userName, setUserName] = useState<string>();

  useEffect(() => {
    AdministrationApi.getAllChats()
      .then((dialogsData) => setDialogs(dialogsData));
  }, []);

  const openDialog = useCallback(
    (
      dialogId: string,
      pickedUserName: string,
    ) => {
      if (openedDialogId !== dialogId) {
        setOpenedDialogId(dialogId);
        setUserName(pickedUserName);
      }
    },
    [openedDialogId],
  );

  return (
    <div className={cn.chatContainer}>
      {
        dialogs && (
          <Dialogs
            dialogs={dialogs}
            openedDialogId={openedDialogId}
            openDialog={openDialog}
          />
        )
      }
      {openedDialogId && (
        <OpenedDialog userName={userName} openedDialogId={openedDialogId} />
      )}
    </div>
  );
};
