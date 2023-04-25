import { Tabs, TabsProps } from 'antd';
import { TeamsCreationsRequestStatus } from '../../../shared/lib/Requests.types';
import { CreateTeamsRequestsList } from '../../../widgets/CreateTeamsRequests';
import { PageWithHeaderLayout } from '../../../widgets/PageWithHeaderLayout';

const tabItems: TabsProps['items'] = [{
  key: String(TeamsCreationsRequestStatus.Expectation),
  label: 'Ожидание',
  children: <CreateTeamsRequestsList
    teamRequestsType={TeamsCreationsRequestStatus.Expectation}
  />,
},
{
  key: String(TeamsCreationsRequestStatus.Confirmed),
  label: 'Подтвержденные',
  children: <CreateTeamsRequestsList
    teamRequestsType={TeamsCreationsRequestStatus.Confirmed}
  />,
},
{
  key: String(TeamsCreationsRequestStatus.Rejected),
  label: 'Отклоненные',
  children: <CreateTeamsRequestsList
    teamRequestsType={TeamsCreationsRequestStatus.Rejected}
  />,
}];

export const CreateTeamsRequests = () => (
  <PageWithHeaderLayout title="Заявки на создание команды">
    <Tabs
      tabBarStyle={{
        backgroundColor: '#fff',
        paddingLeft: '24px',
      }}
      items={tabItems}
      defaultActiveKey={tabItems[0].key}
    />
  </PageWithHeaderLayout>
);
