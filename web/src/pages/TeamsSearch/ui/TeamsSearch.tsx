import { TeamsRequestsList } from '../../../widgets/TeamsRequestsList';
import { PageWithHeaderLayout } from '../../../widgets/PageWithHeaderLayout';

export const TeamsSearch = () => (
  <PageWithHeaderLayout title="Заявки на поиск команды">
    <TeamsRequestsList />
  </PageWithHeaderLayout>
);
