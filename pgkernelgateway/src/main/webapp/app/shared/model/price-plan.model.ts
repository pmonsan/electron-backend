import { Moment } from 'moment';

export interface IPricePlan {
  id?: number;
  label?: string;
  startDate?: Moment;
  endDate?: Moment;
  active?: boolean;
}

export class PricePlan implements IPricePlan {
  constructor(public id?: number, public label?: string, public startDate?: Moment, public endDate?: Moment, public active?: boolean) {
    this.active = this.active || false;
  }
}
