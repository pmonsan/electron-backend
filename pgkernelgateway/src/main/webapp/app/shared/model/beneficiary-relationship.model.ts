export interface IBeneficiaryRelationship {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class BeneficiaryRelationship implements IBeneficiaryRelationship {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
