/* eslint-disable react/require-default-props */
import cn from './CardLogo.module.scss';

interface CardLogoProps {
  src?: string;
  alt?: string
}

export const CardLogo = ({ src, alt = 'logo' }: CardLogoProps) => (
  src ? <img className={cn.cardLogo} src={src} alt={alt} /> : null
);
