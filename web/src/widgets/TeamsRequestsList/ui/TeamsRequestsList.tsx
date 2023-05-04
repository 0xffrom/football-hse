import { useEffect, useMemo, useState } from 'react';
import { AdministrationApi } from '../../../shared/api';
import { RequestCardList } from '../../../features/RequestCardList';
import { RequestCardProps } from '../../../shared/ui/RequestCard';
import { AppRoutes } from '../../../shared/routes';
import { PlayerRequest } from '../../../shared/lib/Requests.types';
import { deserialize, PLAYER_APPLICATION } from '../../../shared/lib/deserialize.utils';

import cn from './TeamsRequestsList.module.scss';

export const TeamsRequestsList = () => {
  const [requests, setRequests] = useState<PlayerRequest[] | null>(null);

  useEffect(() => {
    AdministrationApi.getPlayerApplications().then((data) => setRequests(data));
  }, []);

  const requestsCards: RequestCardProps[] | null = useMemo(() => {
    if (!requests) return null;

    return requests.map((request) => ({
      header: request.name ?? '',
      content: {
        title: deserialize(request.footballPosition, PLAYER_APPLICATION.footballPosition),
        description: request.footballExperience ?? '',
        attention: request.attention,
      },
      redirectTo: `${AppRoutes.TeamSearch}/${request.id}`,
    }));
  }, [requests]);

  return (
    <div className={cn.list}>
      <RequestCardList requestsCards={requestsCards} />
    </div>
  );
};
