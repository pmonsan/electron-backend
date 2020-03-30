import { Moment } from 'moment';

export interface IPriceCommission {
  id?: number;
  code?: string;
  label?: string;
  amount?: number;
  amountInPercent?: boolean;
  dateCreation?: Moment;
  percent?: number;
  currencyCode?: string;
  partnerCode?: string;
  serviceCode?: string;
  description?: string;
  active?: boolean;
}

export class PriceCommission implements IPriceCommission {
  constructor(
    public id?: number,
    public code?: string,
    public label?: string,
    public amount?: number,
    public amountInPercent?: boolean,
    public dateCreation?: Moment,
    public percent?: number,
    public currencyCode?: string,
    public partnerCode?: string,
    public serviceCode?: string,
    public description?: string,
    public active?: boolean
  ) {
    this.amountInPercent = this.amountInPercent || false;
    this.active = this.active || false;
  }
}
