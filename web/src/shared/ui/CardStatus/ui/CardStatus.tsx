import { Space, Typography } from 'antd';
import classNames from 'classnames';
import { TeamsCreationsRequestStatus } from '../../../lib/Requests.types';
import cn from './CardStatus.module.scss';

const { Text } = Typography;

export interface CardStatusProps {
  type: TeamsCreationsRequestStatus
}

const cardStatusTypeToLabel: Record<TeamsCreationsRequestStatus, string> = {
  [TeamsCreationsRequestStatus.Confirmed]: 'Подтверждена',
  [TeamsCreationsRequestStatus.Expectation]: 'Ожидание',
  [TeamsCreationsRequestStatus.Rejected]: 'Отклонена',
};

const cardStatusToColor: Record<TeamsCreationsRequestStatus, string> = {
  [TeamsCreationsRequestStatus.Confirmed]: cn.badgeConfirmed,
  [TeamsCreationsRequestStatus.Expectation]: cn.badgeExpectation,
  [TeamsCreationsRequestStatus.Rejected]: cn.badgeRejected,
};

export const CardStatus = ({ type }: CardStatusProps) => (
  <Space size={6}>
    <div className={classNames(cn.badge, cardStatusToColor[type])} />
    <Text type="secondary">{cardStatusTypeToLabel[type]}</Text>
  </Space>
);
