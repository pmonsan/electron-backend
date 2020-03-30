import { Moment } from 'moment';

export interface IForex {
  id?: number;
  code?: string;
  rate?: number;
  creationDate?: Moment;
  active?: boolean;
  fromCurrencyId?: number;
  toCurrencyId?: number;
}

export class Forex implements IForex {
  constructor(
    public id?: number,
    public code?: string,
    public rate?: number,
    public creationDate?: Moment,
    public active?: boolean,
    public fromCurrencyId?: number,
    public toCurrencyId?: number
  ) {
    this.active = this.active || false;
  }
}
