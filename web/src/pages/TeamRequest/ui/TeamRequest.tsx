import { Button, Space, Typography } from 'antd';
import {
  useCallback, useEffect, useMemo, useState,
} from 'react';
import { useParams } from 'react-router-dom';
import { Description } from '../../../shared/ui/Description';
import { CardStatus } from '../../../shared/ui/CardStatus';
import { DetailedCard } from '../../../shared/ui/DetailedCard';
import { TeamsCreationsRequest, TeamsCreationsRequestStatus } from '../../../shared/lib/Requests.types';
import { AdministrationApi } from '../../../shared/api';

import cn from './TeamRequest.module.scss';

const { Text } = Typography;

export const TeamRequest = () => {
  const { id } = useParams();

  const [data, setData] = useState<TeamsCreationsRequest | null>(null);

  const cardTitle = useMemo(
    () => data && (
      <Space>
        <Text style={{ fontSize: '16px' }}>
          {data.name}
        </Text>
        <CardStatus type={data.status} />
      </Space>
    ),
    [data],
  );

  const updateData = useCallback(
    () => {
      AdministrationApi.getTeamCreateRequestById(
        Number(id),
      ).then((dt) => setData(dt));
    },
    [id],
  );

  const updateStatus = useCallback(
    async (
      teamData: TeamsCreationsRequest,
      status: TeamsCreationsRequestStatus,
    ) => {
      await AdministrationApi.updateTeamCreateRequest(teamData.id, { ...teamData, status });
      updateData();
    },
    [updateData],
  );

  const cardExtra = useMemo(
    () => (data?.status === TeamsCreationsRequestStatus.Expectation
      ? (
        <Space size={24}>
          <Button
            onClick={() => updateStatus(
              data,
              TeamsCreationsRequestStatus.Rejected,
            )}
          >
            Отклонить
          </Button>
          <Button
            type="primary"
            onClick={() => updateStatus(
              data,
              TeamsCreationsRequestStatus.Confirmed,
            )}
          >
            Одобрить
          </Button>
        </Space>
      )
      : null),
    [data, updateStatus],
  );

  useEffect(() => {
    updateData();
  }, [updateData]);

  return (
    <div className={cn.wrapper}>
      <DetailedCard title={cardTitle} extra={cardExtra}>
        <Space direction="vertical" size={24}>
          { data?.captainName && <Description title="Капитан команды" description={data?.captainName} /> }
          { data?.about && <Description title="Информация о команде" description={data?.about} /> }
        </Space>
      </DetailedCard>
    </div>
  );
};
