import { Typography } from 'antd';

import { ChatRequest } from '../../../shared/lib/Requests.types';
import { DialogPreview } from '../../DialogPreview';

import cn from './Dialogs.module.scss';

const { Text } = Typography;

interface DialogsProps {
  openDialog: (dialogId: string, pickedUserName: string) => void;
  dialogs?: ChatRequest[];
  openedDialogId?: string
}

export const Dialogs = ({ openDialog, dialogs, openedDialogId }: DialogsProps) => (
  <div className={cn.dialogsCard}>
    <div className={cn.dialogsCardTitle}>
      <Text strong>Диалоги</Text>
    </div>

    <div className={cn.dialogsCardContent}>
      {dialogs?.map((dialog) => (
        <DialogPreview
          key={dialog.id}
          dialog={dialog}
          openedDialogId={openedDialogId}
          openDialog={openDialog}
        />

      ))}
    </div>
  </div>
);
