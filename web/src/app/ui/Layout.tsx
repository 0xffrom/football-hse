import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import { DashboardOutlined, TableOutlined } from '@ant-design/icons';
import {
  Layout as AntLayout,
  Button,
  Menu,
} from 'antd';
import type { MenuProps } from 'antd';

import { createElement, useMemo } from 'react';
import { AppRoutes } from '../../shared/routes';

import cn from './Layout.module.scss';

const { Header, Content, Sider } = AntLayout;

export const Layout = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const menuItems: MenuProps['items'] = [
    {
      key: 'Заявки',
      label: 'Заявки',
      icon: createElement(DashboardOutlined),
      children: [
        {
          key: AppRoutes.TeamSearch,
          label: 'Поиск команды',
          onClick: (info) => navigate(info.key),
        },
        {
          key: AppRoutes.PlayersSearch,
          label: 'Поиск игроков',
          onClick: (info) => navigate(info.key),
        },
        {
          key: AppRoutes.CreateTeamsRequests,
          label: 'Создание команды',
          onClick: (info) => navigate(info.key),
        },
      ],
    },
    {
      key: AppRoutes.Support,
      label: 'Поддержка',
      onClick: (info) => navigate(info.key),
      icon: createElement(TableOutlined),
    },
  ];

  const defaultSelectedKey = useMemo(() => {
    const paths = [
      AppRoutes.TeamSearch,
      AppRoutes.PlayersSearch,
      AppRoutes.CreateTeamsRequests,
      AppRoutes.Support,
    ];
    return paths.filter((path) => location.pathname.includes(path));
  }, [location.pathname]);

  return (
    <AntLayout className={cn.layout}>
      <Header className={cn.header}>
        <h2>HSE FOOTBALL</h2>
        <h3>Администратор</h3>
      </Header>
      <AntLayout>
        <Sider width={200}>
          <Menu
            defaultSelectedKeys={defaultSelectedKey}
            defaultOpenKeys={['Заявки']}
            className={cn.menu}
            mode="inline"
            items={menuItems}
          />
          <div className={cn.logout}>
            <Button
              onClick={() => {
                localStorage.clear();
                navigate(AppRoutes.Login);
              }}
              danger
              type="text"
            >
              Выйти

            </Button>
          </div>
        </Sider>

        <AntLayout>
          <Content className={cn.content}>
            <Outlet />
          </Content>
        </AntLayout>

      </AntLayout>
    </AntLayout>
  );
};
