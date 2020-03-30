export interface ILimitMeasure {
  id?: number;
  label?: string;
  active?: boolean;
}

export class LimitMeasure implements ILimitMeasure {
  constructor(public id?: number, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
