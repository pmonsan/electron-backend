import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  AccountFeatureComponent,
  AccountFeatureDetailComponent,
  AccountFeatureUpdateComponent,
  AccountFeatureDeletePopupComponent,
  AccountFeatureDeleteDialogComponent,
  accountFeatureRoute,
  accountFeaturePopupRoute
} from './';

const ENTITY_STATES = [...accountFeatureRoute, ...accountFeaturePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AccountFeatureComponent,
    AccountFeatureDetailComponent,
    AccountFeatureUpdateComponent,
    AccountFeatureDeleteDialogComponent,
    AccountFeatureDeletePopupComponent
  ],
  entryComponents: [
    AccountFeatureComponent,
    AccountFeatureUpdateComponent,
    AccountFeatureDeleteDialogComponent,
    AccountFeatureDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayAccountFeatureModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
