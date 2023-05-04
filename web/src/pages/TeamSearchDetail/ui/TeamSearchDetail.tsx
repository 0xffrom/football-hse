import { useEffect, useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Button, Space } from 'antd';
import { CardLogo } from '../../../shared/ui/CardLogo';
import { AdministrationApi } from '../../../shared/api';
import { PlayerRequest } from '../../../shared/lib/Requests.types';
import { AppRoutes } from '../../../shared/routes';
import { Description } from '../../../shared/ui/Description';
import { DetailedCard } from '../../../shared/ui/DetailedCard';
import { PLAYER_APPLICATION, deserialize } from '../../../shared/lib/deserialize.utils';

import cn from './TeamSearchDetail.module.scss';

export const TeamSearchDetail = () => {
  const { id } = useParams();
  const [fullPlayerData, setFullPlayerData] = useState<PlayerRequest | null>();

  const navigate = useNavigate();

  useEffect(() => {
    AdministrationApi.getPlayerApplicationById(
      Number(id),
    ).then((data) => setFullPlayerData(data));
  }, [id]);

  const cardExtra = useMemo(() => (
    <Button
      type="primary"
      onClick={() => {
        AdministrationApi.deletePlayerApplication(Number(id));
        navigate(AppRoutes.TeamSearch);
      }}
    >
      Удалить заявку
    </Button>
  ), [id, navigate]);

  if (!fullPlayerData) return null;

  return (
    <div className={cn.wrapper}>
      <DetailedCard attention={fullPlayerData.attention} title={fullPlayerData.name} extra={cardExtra}>
        <Space direction="vertical">
          <CardLogo src={fullPlayerData.photo} alt={fullPlayerData.name} />
          <Description
            title="Амплуа"
            description={deserialize(fullPlayerData.footballPosition, PLAYER_APPLICATION.footballPosition)}
          />
          {fullPlayerData.faculty && (
          <Description
            title="Факультет, курс (для выпускников - год выпуска)"
            description={fullPlayerData.faculty}
          />
          )}

          <Description
            title="Предпочтительные турниры"
            description={deserialize(fullPlayerData.preferredTournaments, PLAYER_APPLICATION.preferredTournaments)}
          />

          {
            fullPlayerData.footballExperience && (
              <Description
                title="Футбольный опыт"
                description={fullPlayerData.footballExperience}
              />
            )
          }
          {
            fullPlayerData.tournamentExperience && (
            <Description
              title="Игровой опыт в турнирах ВШЭ"
              description={fullPlayerData.tournamentExperience}
            />
            )
          }
          {fullPlayerData.playerPhoneNumber && (
          <Description
            title="Контактная информация"
            description={fullPlayerData.playerPhoneNumber}
          />
          )}

        </Space>
      </DetailedCard>
    </div>
  );
};
