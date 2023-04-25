import { Button, Space } from 'antd';
import { useEffect, useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { CardLogo } from '../../../shared/ui/CardLogo';
import { AppRoutes } from '../../../shared/routes';
import { AdministrationApi } from '../../../shared/api';
import { TeamRequest } from '../../../shared/lib/Requests.types';
import { Description } from '../../../shared/ui/Description';
import { DetailedCard } from '../../../shared/ui/DetailedCard';

import cn from './PlayerSearchDetail.module.scss';

export const PlayerSearchDetail = () => {
  const { id } = useParams();
  const [teamData, setTeamData] = useState<TeamRequest | null>(null);

  const navigate = useNavigate();

  useEffect(() => {
    AdministrationApi.getTeamApplicationById(
      Number(id),
    ).then((data) => setTeamData(data));
  }, [id]);

  const cardExtra = useMemo(() => (
    <Button
      type="primary"
      onClick={() => {
        AdministrationApi.deleteTeamApplication(Number(id));
        navigate(AppRoutes.PlayersSearch);
      }}
    >
      Удалить заявку
    </Button>
  ), [id, navigate]);

  if (!teamData) return null;

  return (
    <div className={cn.wrapper}>
      <DetailedCard attention={teamData.attention} title={teamData.name} extra={cardExtra}>
        <Space direction="vertical">
          <CardLogo src={teamData.logo} alt={teamData.name} />
          <Description
            title="Позиции"
            description={teamData.playerPosition}
          />
          <Description
            title="Турнир"
            description={teamData.tournaments}
          />
          {
            teamData.contact && (
            <Description
              title="Контактная информация"
              description={teamData.contact}
            />
            )
          }
          {
            teamData.description && (
              <Description
                title="Дополнительная информация"
                description={teamData.description}
              />
            )
          }
        </Space>
      </DetailedCard>
    </div>
  );
};
