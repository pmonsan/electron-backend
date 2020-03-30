import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  FeatureComponent,
  FeatureDetailComponent,
  FeatureUpdateComponent,
  FeatureDeletePopupComponent,
  FeatureDeleteDialogComponent,
  featureRoute,
  featurePopupRoute
} from './';

const ENTITY_STATES = [...featureRoute, ...featurePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FeatureComponent,
    FeatureDetailComponent,
    FeatureUpdateComponent,
    FeatureDeleteDialogComponent,
    FeatureDeletePopupComponent
  ],
  entryComponents: [FeatureComponent, FeatureUpdateComponent, FeatureDeleteDialogComponent, FeatureDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayFeatureModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
