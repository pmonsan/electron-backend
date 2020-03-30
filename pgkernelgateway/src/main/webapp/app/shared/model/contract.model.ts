import { Moment } from 'moment';

export interface IContract {
  id?: number;
  number?: string;
  creationDate?: Moment;
  isMerchantContract?: boolean;
  modificationDate?: Moment;
  validationDate?: Moment;
  filename?: string;
  customerCode?: string;
  active?: boolean;
  accountId?: number;
}

export class Contract implements IContract {
  constructor(
    public id?: number,
    public number?: string,
    public creationDate?: Moment,
    public isMerchantContract?: boolean,
    public modificationDate?: Moment,
    public validationDate?: Moment,
    public filename?: string,
    public customerCode?: string,
    public active?: boolean,
    public accountId?: number
  ) {
    this.isMerchantContract = this.isMerchantContract || false;
    this.active = this.active || false;
  }
}
