import {
  ChangeEvent,
  Dispatch, SetStateAction, useCallback, useState,
} from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Input } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { LockOutlined } from '@ant-design/icons/lib/icons';
import { AppRoutes } from '../../../shared/routes';
import { AuthService } from '../../../shared/api/lib/AuthService.util';
import Logo from '../../../assets/Logo.png';

import cn from './Login.module.scss';

export const Login = () => {
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');

  const navigate = useNavigate();

  const onValueChange = useCallback(
    (
      event: ChangeEvent<HTMLInputElement>,
      setValue: Dispatch<SetStateAction<string>>,
    ) => {
      setValue(event.target.value);
    },
    [],
  );

  const onLoginChange = useCallback(
    (event: ChangeEvent<HTMLInputElement>) => {
      onValueChange(event, setLogin);
    },
    [onValueChange],
  );

  const onPasswordChange = useCallback(
    (event: ChangeEvent<HTMLInputElement>) => {
      onValueChange(event, setPassword);
    },
    [onValueChange],
  );

  const onAuth = useCallback(
    () => {
      const newPassword = login + password;
      AuthService.validatePassword(newPassword)
        .then((isValid) => {
          if (isValid) {
            AuthService.setPassword(newPassword);
            navigate(AppRoutes.CreateTeamsRequests);
          }
        });
    },
    [login, navigate, password],
  );

  return (
    <div className={cn.login}>
      <div className={cn.loginBlock}>
        <div className={cn.logoContainer}>
          <img src={Logo} alt="applogo" />
          <h2 className={cn.appName}>HSE FOOTBALL</h2>
          <span className={cn.appDesc}>ADMIN PANEL</span>
        </div>

        <div className={cn.loginForm}>
          <Input
            value={login}
            onChange={onLoginChange}
            placeholder="Логин"
            prefix={<UserOutlined />}
          />
          <Input
            value={password}
            onChange={onPasswordChange}
            placeholder="Пароль"
            type="password"
            prefix={<LockOutlined />}
          />
          <Button
            block
            type="primary"
            onClick={onAuth}
          >
            Войти
          </Button>
        </div>
      </div>
    </div>
  );
};
