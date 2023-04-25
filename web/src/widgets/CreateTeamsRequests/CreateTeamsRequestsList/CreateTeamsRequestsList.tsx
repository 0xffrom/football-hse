import { useState, useEffect, useMemo } from 'react';
import { TeamsCreationsRequest, TeamsCreationsRequestStatus } from '../../../shared/lib/Requests.types';
import { AdministrationApi } from '../../../shared/api';
import { RequestCardList } from '../../../features/RequestCardList';
import { AppRoutes } from '../../../shared/routes';
import { RequestCardProps } from '../../../shared/ui/RequestCard';

import cn from './CreateTeamsRequestsList.module.scss';

interface CreateTeamsRequestsListProps {
  teamRequestsType: TeamsCreationsRequestStatus
}

export const CreateTeamsRequestsList = ({
  teamRequestsType,
}: CreateTeamsRequestsListProps) => {
  const [requests, setRequests] = useState<TeamsCreationsRequest[] | null>(null);

  useEffect(() => {
    AdministrationApi.getTeamCreateApplicationsByStatus(teamRequestsType)
      .then((data) => setRequests(data));
  }, []);

  const requestsCards: RequestCardProps[] | null = useMemo(() => {
    if (!requests) return null;

    return requests.map((request) => ({
      header: request.name ?? '',
      content: {
        title: request.captainName ?? '',
        description: request.about ?? '',
      },
      redirectTo: `${AppRoutes.CreateTeamsRequests}/${request.id}`,
    }));
  }, [requests]);

  return (
    <div className={cn.list}>
      <RequestCardList requestsCards={requestsCards} />
    </div>
  );
};
