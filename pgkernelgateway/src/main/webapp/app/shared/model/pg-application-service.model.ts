export interface IPgApplicationService {
  id?: number;
  serviceCode?: string;
  active?: boolean;
  pgApplicationId?: number;
}

export class PgApplicationService implements IPgApplicationService {
  constructor(public id?: number, public serviceCode?: string, public active?: boolean, public pgApplicationId?: number) {
    this.active = this.active || false;
  }
}
