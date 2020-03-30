export interface IBeneficiaryType {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class BeneficiaryType implements IBeneficiaryType {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
