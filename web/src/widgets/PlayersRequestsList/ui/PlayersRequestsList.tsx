import { useEffect, useMemo, useState } from 'react';
import { AdministrationApi } from '../../../shared/api';
import { RequestCardList } from '../../../features/RequestCardList';
import { RequestCardProps } from '../../../shared/ui/RequestCard';
import { AppRoutes } from '../../../shared/routes';
import { TeamRequest } from '../../../shared/lib/Requests.types';
import { deserialize, TEAM_APPLICATION } from '../../../shared/lib/deserialize.utils';

import cn from './PlayersRequestsList.module.scss';

export const PlayersRequestsList = () => {
  const [requests, setRequests] = useState<TeamRequest[] | null>(null);

  useEffect(() => {
    AdministrationApi.getTeamApplications().then((data) => setRequests(data));
  }, []);

  const requestsCards: RequestCardProps[] | null = useMemo(() => {
    if (!requests) return null;

    return requests.map((request) => ({
      header: request.name ?? '',
      content: {
        title: deserialize(request.playerPosition, TEAM_APPLICATION.playerPosition),
        description: deserialize(request.tournaments, TEAM_APPLICATION.tournaments),
        attention: request.attention,
      },
      redirectTo: `${AppRoutes.PlayersSearch}/${request.id}`,
    }));
  }, [requests]);

  return (
    <div className={cn.list}>
      <RequestCardList requestsCards={requestsCards} />
    </div>
  );
};
