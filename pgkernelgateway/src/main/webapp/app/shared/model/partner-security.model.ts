export interface IPartnerSecurity {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class PartnerSecurity implements IPartnerSecurity {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
