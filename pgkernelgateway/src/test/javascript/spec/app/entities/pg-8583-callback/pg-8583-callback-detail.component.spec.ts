/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { Pg8583CallbackDetailComponent } from 'app/entities/pg-8583-callback/pg-8583-callback-detail.component';
import { Pg8583Callback } from 'app/shared/model/pg-8583-callback.model';

describe('Component Tests', () => {
  describe('Pg8583Callback Management Detail Component', () => {
    let comp: Pg8583CallbackDetailComponent;
    let fixture: ComponentFixture<Pg8583CallbackDetailComponent>;
    const route = ({ data: of({ pg8583Callback: new Pg8583Callback(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [Pg8583CallbackDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(Pg8583CallbackDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Pg8583CallbackDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pg8583Callback).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
