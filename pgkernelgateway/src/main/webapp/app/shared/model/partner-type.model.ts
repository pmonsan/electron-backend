export interface IPartnerType {
  id?: number;
  code?: string;
  label?: string;
  description?: string;
  active?: boolean;
}

export class PartnerType implements IPartnerType {
  constructor(public id?: number, public code?: string, public label?: string, public description?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
