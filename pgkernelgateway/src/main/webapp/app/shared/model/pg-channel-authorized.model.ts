import { Moment } from 'moment';

export interface IPgChannelAuthorized {
  id?: number;
  transactionTypeCode?: string;
  registrationDate?: Moment;
  active?: boolean;
  pgChannelId?: number;
}

export class PgChannelAuthorized implements IPgChannelAuthorized {
  constructor(
    public id?: number,
    public transactionTypeCode?: string,
    public registrationDate?: Moment,
    public active?: boolean,
    public pgChannelId?: number
  ) {
    this.active = this.active || false;
  }
}
