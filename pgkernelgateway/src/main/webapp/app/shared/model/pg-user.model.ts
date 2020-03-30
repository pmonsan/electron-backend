import { Moment } from 'moment';

export interface IPgUser {
  id?: number;
  username?: string;
  email?: string;
  firstname?: string;
  name?: string;
  msisdn?: string;
  creationDate?: Moment;
  updateDate?: Moment;
  userProfileId?: number;
}

export class PgUser implements IPgUser {
  constructor(
    public id?: number,
    public username?: string,
    public email?: string,
    public firstname?: string,
    public name?: string,
    public msisdn?: string,
    public creationDate?: Moment,
    public updateDate?: Moment,
    public userProfileId?: number
  ) {}
}
