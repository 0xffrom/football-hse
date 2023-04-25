import { Space } from 'antd';
import { RequestCard, RequestCardProps } from '../../../shared/ui/RequestCard';
import cn from './RequestCardList.module.scss';

export interface RequestCardListProps {
  requestsCards: RequestCardProps[] | null
}

export const RequestCardList = ({ requestsCards }: RequestCardListProps) => (
  <Space className={cn.list} size={12} direction="vertical">
    {/* eslint-disable-next-line react/no-array-index-key */}
    {requestsCards?.map((card, i) => <RequestCard {...card} key={i} />)}
  </Space>
);
