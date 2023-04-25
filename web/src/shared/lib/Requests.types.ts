export interface PlayerRequest {
  id: number;
  playerPhoneNumber?: string;
  footballPosition: number;
  preferredTournaments: number;
  faculty?: string;
  footballExperience?: string;
  tournamentExperience?: string;
  contact?: string;
  name?: string
  photo?: string;
  attention: boolean
}

export interface TeamRequest {
  id: number;
  teamId: number;
  playerPosition: number;
  tournaments: number;
  description?: string;
  name?: string;
  logo?: string;
  contact?: string;
  attention: boolean
}

export interface TeamsCreationsRequest {
  id: number;
  name?: string;
  captainPhoneNumber?: string;
  captainName?: string;
  logo?: string;
  about?: string;
  status: TeamsCreationsRequestStatus
}

export interface MessageRequest {
  id: number;
  sendTime?: string;
  text?: string;
  isRead: boolean;
  sender?: string;
  receiver?: string;
}

export interface ChatRequest {
  id: number;
  lastMessage: MessageRequest
  phoneNumber1?: string;
  name1?: string;
  photo1?: string;
  phoneNumber2?: string;
  name2?: string;
  photo2?: string;
}

export enum TeamsCreationsRequestStatus {
  Expectation = 0,
  Confirmed = 1,
  Rejected = 2,
}
