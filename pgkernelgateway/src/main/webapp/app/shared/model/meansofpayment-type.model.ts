export interface IMeansofpaymentType {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class MeansofpaymentType implements IMeansofpaymentType {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
