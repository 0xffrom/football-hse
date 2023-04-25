export const basePath = 'http://hse-football.ru/api/Administration';

export const customFetch = <T>(
  url: string,
  params?:RequestInit,
  authNeeded: boolean = true,
  expectResponse: boolean = true,
): Promise<T> => {
  if (authNeeded) {
    const password = localStorage.getItem('password');

    return fetch(`${basePath}/${password}/${url}`, {
      ...params,
    }).then((response) => {
      if (!response.ok) {
        throw new Error(response.statusText);
      }
      return expectResponse && response.json();
    });
  }

  return fetch(`${basePath}/${url}`, {
    ...params,
  }).then((response) => {
    if (!response.ok) {
      throw new Error(response.statusText);
    }
    return response.json();
  });
};
