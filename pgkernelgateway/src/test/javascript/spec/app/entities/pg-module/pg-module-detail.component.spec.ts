/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgModuleDetailComponent } from 'app/entities/pg-module/pg-module-detail.component';
import { PgModule } from 'app/shared/model/pg-module.model';

describe('Component Tests', () => {
  describe('PgModule Management Detail Component', () => {
    let comp: PgModuleDetailComponent;
    let fixture: ComponentFixture<PgModuleDetailComponent>;
    const route = ({ data: of({ pgModule: new PgModule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgModuleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgModuleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgModuleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgModule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
