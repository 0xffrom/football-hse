import { basePath, customFetch } from './lib/customFetch.util';
import {
  ChatRequest,
  MessageRequest,
  PlayerRequest,
  TeamRequest,
  TeamsCreationsRequest,
  TeamsCreationsRequestStatus,
} from '../lib/Requests.types';

const PlayerApplications = 'PlayerApplications';
const TeamApplications = 'TeamApplications';
const Teams = 'Teams';

export const AdministrationApi = {
  getPlayerApplications: () => customFetch<PlayerRequest[]>(
    `${PlayerApplications}`,
  ),
  getPlayerApplicationById: (id: number) => customFetch<PlayerRequest>(
    `${PlayerApplications}/${id}`,
  ),
  deletePlayerApplication: (id: number) => customFetch(
    `${PlayerApplications}/${id}`,
    {
      method: 'DELETE',
    },
  ),

  getTeamApplications: () => customFetch<TeamRequest[]>(
    `${TeamApplications}`,
  ),
  getTeamApplicationById: (id: number) => customFetch<TeamRequest>(
    `${TeamApplications}/${id}`,
  ),
  deleteTeamApplication: (id: number) => customFetch(
    `${TeamApplications}/${id}`,
    {
      method: 'DELETE',
    },
  ),

  getTeamCreateApplicationsByStatus: (
    status: TeamsCreationsRequestStatus,
  ) => customFetch<TeamsCreationsRequest[]>(
    `${Teams}/filters?status=${status}`,
  ),
  getTeamCreateRequestById: (id: number) => customFetch<TeamsCreationsRequest>(
    `${Teams}/${id}`,
  ),
  updateTeamCreateRequest: (
    id: number,
    newTeamRequest: Partial<TeamsCreationsRequest>,
  ) => customFetch(
    `${Teams}/${id}`,
    {
      method: 'PUT',
      body: JSON.stringify({
        ...newTeamRequest,
      }),
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
      },
    },
    true,
    false,
  ),

  getAllChats: () => customFetch<ChatRequest[]>(
    'Chats',
  ),

  getMessages: (userPhone: string, loadNumber: number = 0) => customFetch<MessageRequest[]>(
    `Messages/${userPhone}/${loadNumber}`,
  ),

  sendMessage: (message: Partial<MessageRequest>) => customFetch(
    'Messages',
    {
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
      },
      method: 'POST',
      body: JSON.stringify(message),
    },
  ),

  authorize: (password: string) => fetch(`${basePath}/Authorize/${password}`).then((data) => data.ok),
};
