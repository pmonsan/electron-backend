import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  CustomerBlacklistComponent,
  CustomerBlacklistDetailComponent,
  CustomerBlacklistUpdateComponent,
  CustomerBlacklistDeletePopupComponent,
  CustomerBlacklistDeleteDialogComponent,
  customerBlacklistRoute,
  customerBlacklistPopupRoute
} from './';

const ENTITY_STATES = [...customerBlacklistRoute, ...customerBlacklistPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CustomerBlacklistComponent,
    CustomerBlacklistDetailComponent,
    CustomerBlacklistUpdateComponent,
    CustomerBlacklistDeleteDialogComponent,
    CustomerBlacklistDeletePopupComponent
  ],
  entryComponents: [
    CustomerBlacklistComponent,
    CustomerBlacklistUpdateComponent,
    CustomerBlacklistDeleteDialogComponent,
    CustomerBlacklistDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayCustomerBlacklistModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
