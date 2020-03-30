import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PricePlanComponent,
  PricePlanDetailComponent,
  PricePlanUpdateComponent,
  PricePlanDeletePopupComponent,
  PricePlanDeleteDialogComponent,
  pricePlanRoute,
  pricePlanPopupRoute
} from './';

const ENTITY_STATES = [...pricePlanRoute, ...pricePlanPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PricePlanComponent,
    PricePlanDetailComponent,
    PricePlanUpdateComponent,
    PricePlanDeleteDialogComponent,
    PricePlanDeletePopupComponent
  ],
  entryComponents: [PricePlanComponent, PricePlanUpdateComponent, PricePlanDeleteDialogComponent, PricePlanDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPricePlanModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
