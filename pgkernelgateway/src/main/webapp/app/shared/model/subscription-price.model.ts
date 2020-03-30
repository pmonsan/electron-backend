import { Moment } from 'moment';

export interface ISubscriptionPrice {
  id?: number;
  amount?: number;
  description?: string;
  label?: string;
  modificationDate?: Moment;
  serviceCode?: string;
  accountTypeCode?: string;
  currencyCode?: string;
  active?: boolean;
  pricePlanId?: number;
}

export class SubscriptionPrice implements ISubscriptionPrice {
  constructor(
    public id?: number,
    public amount?: number,
    public description?: string,
    public label?: string,
    public modificationDate?: Moment,
    public serviceCode?: string,
    public accountTypeCode?: string,
    public currencyCode?: string,
    public active?: boolean,
    public pricePlanId?: number
  ) {
    this.active = this.active || false;
  }
}
