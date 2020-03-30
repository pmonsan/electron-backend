export interface IPeriodicity {
  id?: number;
  label?: string;
  active?: boolean;
}

export class Periodicity implements IPeriodicity {
  constructor(public id?: number, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
