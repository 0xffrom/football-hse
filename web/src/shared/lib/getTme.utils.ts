export const getTime = (date: string) => {
  if (!Object.is(NaN, Number(new Date(date)))) {
    return `${(new Date(date)).getHours()}:${(new Date(date)).getMinutes()}`;
  }
  return null;
};
