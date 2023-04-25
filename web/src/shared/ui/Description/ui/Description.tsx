import { Space, Typography } from 'antd';

const { Text } = Typography;

export interface DescriptionProps {
  title: string;
  description: string | number
}

export const Description = ({ title, description }: DescriptionProps) => (
  <Space direction="vertical" size={8}>
    <Text type="secondary">{title}</Text>
    <Text>{description}</Text>
  </Space>
);
