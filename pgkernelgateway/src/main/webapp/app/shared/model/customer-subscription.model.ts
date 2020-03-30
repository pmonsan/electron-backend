import { Moment } from 'moment';

export interface ICustomerSubscription {
  id?: number;
  number?: string;
  creationDate?: Moment;
  isMerchantSubscription?: boolean;
  modificationDate?: Moment;
  validationDate?: Moment;
  filename?: string;
  customerCode?: string;
  serviceCode?: string;
  accountNumber?: string;
  startDate?: Moment;
  endDate?: Moment;
  active?: boolean;
}

export class CustomerSubscription implements ICustomerSubscription {
  constructor(
    public id?: number,
    public number?: string,
    public creationDate?: Moment,
    public isMerchantSubscription?: boolean,
    public modificationDate?: Moment,
    public validationDate?: Moment,
    public filename?: string,
    public customerCode?: string,
    public serviceCode?: string,
    public accountNumber?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public active?: boolean
  ) {
    this.isMerchantSubscription = this.isMerchantSubscription || false;
    this.active = this.active || false;
  }
}
