import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PriceCommissionComponent,
  PriceCommissionDetailComponent,
  PriceCommissionUpdateComponent,
  PriceCommissionDeletePopupComponent,
  PriceCommissionDeleteDialogComponent,
  priceCommissionRoute,
  priceCommissionPopupRoute
} from './';

const ENTITY_STATES = [...priceCommissionRoute, ...priceCommissionPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PriceCommissionComponent,
    PriceCommissionDetailComponent,
    PriceCommissionUpdateComponent,
    PriceCommissionDeleteDialogComponent,
    PriceCommissionDeletePopupComponent
  ],
  entryComponents: [
    PriceCommissionComponent,
    PriceCommissionUpdateComponent,
    PriceCommissionDeleteDialogComponent,
    PriceCommissionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPriceCommissionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
