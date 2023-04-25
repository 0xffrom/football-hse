import { Card, Typography } from 'antd';
import { ReactNode } from 'react';
import { AttentionIcon } from '../../AttentionIcon';

import cn from './DetailedCard.module.scss';

const { Text } = Typography;
interface DetailedCardProps {
  title: ReactNode;
  extra: ReactNode;
  attention?: boolean;
  children: ReactNode;
}

export const DetailedCard = ({
  title,
  extra,
  attention,
  children,
}: DetailedCardProps) => (
  <Card
    title={title}
    extra={extra}
    className={cn.card}
  >
    {
      attention && (
        <div className={cn.detailedCardAttentionBanner}>
          <AttentionIcon />
          <Text type="secondary">Мы обнаружили нецензурную лексику в данной заявке</Text>
        </div>
      )
    }
    {children}
  </Card>
);
