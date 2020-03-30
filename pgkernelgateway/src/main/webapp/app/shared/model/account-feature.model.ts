import { Moment } from 'moment';

export interface IAccountFeature {
  id?: number;
  activationDate?: Moment;
  featureCode?: string;
  active?: boolean;
  accountId?: number;
}

export class AccountFeature implements IAccountFeature {
  constructor(
    public id?: number,
    public activationDate?: Moment,
    public featureCode?: string,
    public active?: boolean,
    public accountId?: number
  ) {
    this.active = this.active || false;
  }
}
