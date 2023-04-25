import classNames from 'classnames';

import { ADMIN_PHONE_NUMBER } from '../../../shared/config';
import { MessageRequest } from '../../../shared/lib/Requests.types';
import { getTime } from '../../../shared/lib/getTme.utils';

import cn from './Message.module.scss';

interface MessageProps {
  message: MessageRequest
}

export const Message = ({ message }: MessageProps) => (
  <div
    className={
      classNames(
        cn.message,
        message.sender === ADMIN_PHONE_NUMBER && cn.messageSelf,
      )
    }
  >
    <div className={cn.messageContent}>
      <div>{message.text}</div>
      <div className={cn.messageSendTime}>
        { getTime(message.sendTime ?? '')}
      </div>
    </div>
  </div>
);
