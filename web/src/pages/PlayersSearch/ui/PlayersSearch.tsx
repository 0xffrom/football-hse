import { PlayersRequestsList } from '../../../widgets/PlayersRequestsList';
import { PageWithHeaderLayout } from '../../../widgets/PageWithHeaderLayout';

export const PlayersSearch = () => (
  <PageWithHeaderLayout title="Заявки на поиск игроков">
    <PlayersRequestsList />
  </PageWithHeaderLayout>
);
