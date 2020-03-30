import { Moment } from 'moment';

export const enum CustomerBlacklistStatus {
  INITIATED = 'INITIATED',
  BLACKLIST = 'BLACKLIST',
  CANCELLED = 'CANCELLED'
}

export interface ICustomerBlacklist {
  id?: number;
  customerBlacklistStatus?: CustomerBlacklistStatus;
  insertionDate?: Moment;
  modificationDate?: Moment;
  comment?: string;
  active?: boolean;
  customerId?: number;
}

export class CustomerBlacklist implements ICustomerBlacklist {
  constructor(
    public id?: number,
    public customerBlacklistStatus?: CustomerBlacklistStatus,
    public insertionDate?: Moment,
    public modificationDate?: Moment,
    public comment?: string,
    public active?: boolean,
    public customerId?: number
  ) {
    this.active = this.active || false;
  }
}
