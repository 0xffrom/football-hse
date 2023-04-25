import { ReactNode } from 'react';

import cn from './PageWithHeaderLayout.module.scss';

interface PageWithHeaderLayoutProps {
  title: string | ReactNode;
  children: ReactNode;
}

export const PageWithHeaderLayout = ({ title, children }: PageWithHeaderLayoutProps) => (
  <section className={cn.layout}>
    <div className={cn.header}>
      {
        typeof title === 'string'
          ? <h2>{title}</h2>
          : title
      }
    </div>
    <div className={cn.content}>{children}</div>
  </section>
);
