import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  MeansofpaymentComponent,
  MeansofpaymentDetailComponent,
  MeansofpaymentUpdateComponent,
  MeansofpaymentDeletePopupComponent,
  MeansofpaymentDeleteDialogComponent,
  meansofpaymentRoute,
  meansofpaymentPopupRoute
} from './';

const ENTITY_STATES = [...meansofpaymentRoute, ...meansofpaymentPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MeansofpaymentComponent,
    MeansofpaymentDetailComponent,
    MeansofpaymentUpdateComponent,
    MeansofpaymentDeleteDialogComponent,
    MeansofpaymentDeletePopupComponent
  ],
  entryComponents: [
    MeansofpaymentComponent,
    MeansofpaymentUpdateComponent,
    MeansofpaymentDeleteDialogComponent,
    MeansofpaymentDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayMeansofpaymentModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
