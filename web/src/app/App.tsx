import {
  Route, Routes, useNavigate, Navigate,
} from 'react-router-dom';
import { useEffect } from 'react';
import { PlayersSearch } from '../pages/PlayersSearch';
import { TeamsSearch } from '../pages/TeamsSearch';
import { TeamRequest } from '../pages/TeamRequest';
import { CreateTeamsRequests } from '../pages/CreateTeamsRequests';
import { TeamSearchDetail } from '../pages/TeamSearchDetail';
import { PlayerSearchDetail } from '../pages/PlayerSearchDetail';
import { Support } from '../pages/Support';
import { Login } from '../pages/Login';

import { AppRoutes } from '../shared/routes';
import { Layout } from './ui/Layout';
import { AuthService } from '../shared/api/lib/AuthService.util';

const AuthorizedRoute = ({ children }: { children: JSX.Element }) => {
  const navigate = useNavigate();

  useEffect(() => {
    const password = AuthService.getPassword() ?? '';
    AuthService.validatePassword(password).then((isValid) => {
      if (!isValid) navigate(AppRoutes.Login);
    });
  }, []);

  return children;
};

export const App = () => (
  <Routes>
    <Route path={AppRoutes.Login} element={<Login />} />
    <Route
      path="/"
      element={(
        <AuthorizedRoute>
          <Layout />
        </AuthorizedRoute>
      )}
    >
      <Route
        path=""
        element={(<Navigate to={AppRoutes.CreateTeamsRequests} />
          )}
      />
      <Route
        path={AppRoutes.Team}
        element={<TeamSearchDetail />}
      />
      <Route
        path={AppRoutes.TeamSearch}
        element={<TeamsSearch />}
      />

      <Route
        path={AppRoutes.Player}
        element={<PlayerSearchDetail />}
      />
      <Route
        path={AppRoutes.PlayersSearch}
        element={<PlayersSearch />}
      />

      <Route
        path={AppRoutes.TeamRequest}
        element={<TeamRequest />}
      />
      <Route
        path={AppRoutes.CreateTeamsRequests}
        element={<CreateTeamsRequests />}
      />
      <Route
        path={AppRoutes.Support}
        element={<Support />}
      />
    </Route>
    <Route path="*" element={<Login />} />
  </Routes>
);
