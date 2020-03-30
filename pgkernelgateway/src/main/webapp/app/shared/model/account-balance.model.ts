import { Moment } from 'moment';

export interface IAccountBalance {
  id?: number;
  situationDate?: Moment;
  balance?: number;
  active?: boolean;
  accountId?: number;
}

export class AccountBalance implements IAccountBalance {
  constructor(
    public id?: number,
    public situationDate?: Moment,
    public balance?: number,
    public active?: boolean,
    public accountId?: number
  ) {
    this.active = this.active || false;
  }
}
