import { Moment } from 'moment';

export interface IUserConnection {
  id?: number;
  loginDate?: Moment;
  logoutDate?: Moment;
  userId?: number;
}

export class UserConnection implements IUserConnection {
  constructor(public id?: number, public loginDate?: Moment, public logoutDate?: Moment, public userId?: number) {}
}
