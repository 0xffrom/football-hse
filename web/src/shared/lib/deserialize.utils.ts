const POSITIONS = ['ЦФРВ', 'ЛФРВ', 'ПФРВ', 'ЦАП', 'ЦОП', 'ЛП', 'ПП', 'ЦЗ', 'ЛЗ', 'ПЗ', 'ВРТ'];
const TOURNAMENTS = [
  'Осенний кубок',
  'Футзал Молодежная лига',
  'Футзал Вторая лига',
  'Футзал Первая лига',
  'Футзал Высшая лига',
  'Молодежная лига',
  'Вторая лига',
  'Первая лига',
  'Высшая лига',
  'Летний кубок',
];

export const PLAYER_APPLICATION = {
  preferredTournaments: TOURNAMENTS,
  footballPosition: POSITIONS,
};

export const TEAM_APPLICATION = {
  tournaments: TOURNAMENTS,
  playerPosition: POSITIONS,
};

export const deserialize = (decoder: number, enumArray: string[]) => {
  let value = decoder;
  const result = [];

  for (let i = 0; i < enumArray.length; i += 1) {
    if (value % 2 === 1) {
      result.push(enumArray[i]);
    }
    // eslint-disable-next-line operator-assignment, no-bitwise
    value = value >> 1;
  }
  return result.join(', ');
};
