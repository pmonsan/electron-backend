import { Moment } from 'moment';

export interface IPgBatch {
  id?: number;
  code?: string;
  label?: string;
  expectedEndDate?: Moment;
  batchDate?: Moment;
  active?: boolean;
}

export class PgBatch implements IPgBatch {
  constructor(
    public id?: number,
    public code?: string,
    public label?: string,
    public expectedEndDate?: Moment,
    public batchDate?: Moment,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}
